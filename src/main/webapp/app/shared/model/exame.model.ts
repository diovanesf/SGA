import { IAmostra } from '@/shared/model/amostra.model';

export interface IExame {
  id?: number;
  nome?: string | null;
  tipo?: string | null;
  resultado?: string | null;
  amostra?: IAmostra | null;
}

export class Exame implements IExame {
  constructor(
    public id?: number,
    public nome?: string | null,
    public tipo?: string | null,
    public resultado?: string | null,
    public amostra?: IAmostra | null
  ) {}
}
