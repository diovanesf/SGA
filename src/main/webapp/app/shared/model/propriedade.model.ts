import { IEndereco } from '@/shared/model/endereco.model';

export interface IPropriedade {
  id?: number;
  tipo?: string | null;
  numeroAnimais?: number | null;
  acometidos?: string | null;
  observacoes?: string | null;
  pricipalSuspeita?: string | null;
  endereco?: IEndereco | null;
}

export class Propriedade implements IPropriedade {
  constructor(
    public id?: number,
    public tipo?: string | null,
    public numeroAnimais?: number | null,
    public acometidos?: string | null,
    public observacoes?: string | null,
    public pricipalSuspeita?: string | null,
    public endereco?: IEndereco | null
  ) {}
}
