#!/bin/sh
/deployments/run-java.sh -integraProtheus || echo $? ;
/deployments/run-java.sh -baixarParcelasProtheus || echo $? ;
/deployments/run-java.sh -integrarInclusaoContratoProtheusVendaParcelada || echo $? ;
/deployments/run-java.sh -integrarInclusaoContratoProtheusVendaAvulsa || echo $? ;