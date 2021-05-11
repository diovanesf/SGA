import { IAmostra } from '@/shared/model/amostra.model';

export interface IMidia {
  id?: number;
  nome?: string | null;
  descricao?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  amostra?: IAmostra | null;
}

export class Midia implements IMidia {
  constructor(
    public id?: number,
    public nome?: string | null,
    public descricao?: string | null,
    public fileContentType?: string | null,
    public file?: string | null,
    public amostra?: IAmostra | null
  ) {}
}
