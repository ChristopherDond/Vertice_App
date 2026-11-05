/**
 * Formata CPF para o padrão mascarado esperado pelo UserDto do app Android
 * Exemplo: '52998224725' -> '***.982.247-**'
 */
export function maskCpf(cpf: string): string {
  const clean = cpf.replace(/\D/g, '');
  if (clean.length !== 11) {
    return '***.***.***-**';
  }
  return `***.${clean.substring(3, 6)}.${clean.substring(6, 9)}-**`;
}

/**
 * Remove pontuação de CPF, telefone e CEP
 */
export function cleanDigits(value: string): string {
  return value.replace(/\D/g, '');
}
