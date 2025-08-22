package fieg.modulos.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.dto.SmsEnvioFiltroDTO;
import fieg.modulos.model.SmsEnvio;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
public class SmsEnvioRepository implements PanacheRepositoryBase<SmsEnvio, Integer> {


    public List<SmsEnvio> findSmsByParametros(SmsEnvioFiltroDTO smsEnvioFiltroDTO) {
            Date dataInicial = null;
            Date dataFinal = null;
            StringBuilder sql = new StringBuilder();
            Map<String, Object> params = new HashMap<>();
            sql.append("dataEnvio between :dataInicial and :dataFinal");
            if(smsEnvioFiltroDTO.dataInicial.equals("")) {
                dataInicial = new Date();
            } else {
                dataInicial = transformeDate(smsEnvioFiltroDTO.dataInicial);
            }
             if(smsEnvioFiltroDTO.dataFinal == null){
                smsEnvioFiltroDTO.dataFinal = smsEnvioFiltroDTO.dataInicial;
            }
            dataFinal = colocarUltimaHoraNaData(transformeDate(smsEnvioFiltroDTO.dataFinal));
            params.put("dataInicial",dataInicial);
            params.put("dataFinal", dataFinal);

            if(smsEnvioFiltroDTO.statusEnvio != null){
                sql.append(" and envio = :envio");
                params.put("envio", smsEnvioFiltroDTO.statusEnvio);
            }

            if(!smsEnvioFiltroDTO.telefone.equals("")){
                sql.append(" and sms.to = :telefone");
                params.put("telefone", "55" + smsEnvioFiltroDTO.telefone );
            }

            PanacheQuery<SmsEnvio> panacheQuery = SmsEnvio.find(sql.toString(), params);
            Page page = new Page(smsEnvioFiltroDTO.page, smsEnvioFiltroDTO.pageSize);
            PageResult<SmsEnvio> resultado = new PageResult<>();
            resultado.result = panacheQuery.page(page).list();
            resultado.pageTotal = panacheQuery.pageCount();
            resultado.page = page.index;
            resultado.pageSize =  page.size;

            return resultado.result;


    }

    public SmsEnvio getLastSMS(){
        return find("order by id desc").firstResult();
    }

    public Long findCountSmsByParametros(SmsEnvioFiltroDTO smsEnvioFiltroDTO){
        Date dataInicial;
        Date dataFinal;
        try {
            StringBuilder sql = new StringBuilder();
            Map<String, Object> params = new HashMap<>();
            sql.append("dataEnvio between :dataInicial and :dataFinal");
            if(smsEnvioFiltroDTO.dataInicial == null){
                dataInicial = new Date();
            }else {
                dataInicial = transformeDate(smsEnvioFiltroDTO.dataInicial);
            }
            if(smsEnvioFiltroDTO.dataFinal == null){
                dataFinal = dataInicial;
            } else {
                dataFinal = transformeDate(smsEnvioFiltroDTO.dataFinal);
            }
            dataInicial = colocarPrimeiraHoraNaData(dataInicial);
            dataFinal = colocarUltimaHoraNaData(dataFinal);
            params.put("dataInicial",dataInicial);
            params.put("dataFinal", dataFinal);

            if(smsEnvioFiltroDTO.statusEnvio != null){
                sql.append(" and envio = :envio");
                params.put("envio", smsEnvioFiltroDTO.statusEnvio);
            }
            if(!smsEnvioFiltroDTO.telefone.equals("")){
                sql.append(" and sms.to = :telefone");
                params.put("telefone", "55" + smsEnvioFiltroDTO.telefone );
            }
            PanacheQuery<SmsEnvio> panacheQuery = SmsEnvio.find(sql.toString(), params);
            return panacheQuery.count();
        }catch (Exception e){
            Log.error(e);
            return null;
        }

    }


    private Date colocarUltimaHoraNaData(Date dataFinal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataFinal);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        dataFinal = cal.getTime();
        return dataFinal;
    }

    private Date colocarPrimeiraHoraNaData(Date dataInicial) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicial);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        dataInicial = cal.getTime();
        return dataInicial;
    }

    private Date transformeDate( String dataText){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(dataText);
        } catch (ParseException e) {
            Log.error("Erro ao converter data ", e);
            return null;
        }
    }

}
