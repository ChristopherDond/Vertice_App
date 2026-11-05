import { db } from '../db/database.js';
import { maskCpf } from './formatters.js';

export interface AddressDto {
  cep: string;
  street: string;
  number: string;
  complement: string;
  district: string;
  city: string;
  state: string;
}

export interface UserDto {
  id: string;
  name: string;
  email: string;
  phone: string;
  cpf_masked: string;
  bio: string;
  skills: string[];
  avatar_url: string | null;
  address: AddressDto | null;
  is_pro: boolean;
  violeta_verified: boolean;
  profile_completion: number;
  rating: number;
  completed_services: number;
}

export async function getUserDtoById(userId: string): Promise<UserDto | null> {
  const user = await db
    .selectFrom('usuarios')
    .where('id', '=', userId)
    .selectAll()
    .executeTakeFirst();

  if (!user) return null;

  const endereco = await db
    .selectFrom('enderecos')
    .where('usuario_id', '=', userId)
    .selectAll()
    .executeTakeFirst();

  const address: AddressDto | null = endereco
    ? {
        cep: endereco.cep,
        street: endereco.rua,
        number: endereco.numero,
        complement: endereco.complemento || '',
        district: endereco.district,
        city: endereco.cidade,
        state: endereco.estado,
      }
    : null;

  return {
    id: user.id,
    name: user.name,
    email: user.email,
    phone: user.phone,
    cpf_masked: maskCpf(user.cpf),
    bio: user.bio || '',
    skills: user.skills || [],
    avatar_url: user.avatar_url,
    address,
    is_pro: Boolean(user.is_pro),
    violeta_verified: Boolean(user.violeta_verified),
    profile_completion: Number(user.profile_completion) || 0,
    rating: Number(user.rating) || 0.0,
    completed_services: Number(user.completed_services) || 0,
  };
}
