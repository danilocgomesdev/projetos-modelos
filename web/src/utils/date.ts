export function converteDataStringDate(data: string): Date {
    return new Date(`${data}T00:00:00`);
}

export function formatarDataParaInputDate(dataVencimento: Date) {
    const ano = dataVencimento.getFullYear();
    const mes = String(dataVencimento.getMonth() + 1).padStart(2, "0"); // getMonth() retorna de 0 a 11, por isso somamos 1
    const dia = String(dataVencimento.getDate()).padStart(2, "0"); // Garante que o dia tenha dois dígitos
    return `${ano}-${mes}-${dia}`; // Retorna no formato yyyy-mm-dd
};

export function getTodayISODate() {
    return new Date().toISOString().split("T")[0];
  }