import { IUser } from '@/shared/model/user.model';
import { IMidia } from '@/shared/model/midia.model';
import { IExame } from '@/shared/model/exame.model';
import { IPropriedade } from '@/shared/model/propriedade.model';
import { IMedicoveterinario } from '@/shared/model/medicoveterinario.model';

export interface IAmostra {
  id?: number;
  protocolo?: string | null;
  formaEnvio?: string | null;
  numeroAmostras?: number | null;
  especie?: string | null;
  dataInicial?: Date | null;
  dataFinal?: Date | null;
  materialRecebido?: string | null;
  acondicionamento?: string | null;
  condicaoMaterial?: string | null;
  status?: string | null;
  tipoMedVet?: string | null;
  valorTotal?: number | null;
  tipoPagamento?: string | null;
  situacao?: string | null;
  users?: IUser[] | null;
  midias?: IMidia[] | null;
  exames?: IExame[] | null;
  propriedade?: IPropriedade | null;
  medicoveterinario?: IMedicoveterinario | null;
}

export class Amostra implements IAmostra {
  constructor(
    public id?: number,
    public protocolo?: string | null,
    public formaEnvio?: string | null,
    public numeroAmostras?: number | null,
    public especie?: string | null,
    public dataInicial?: Date | null,
    public dataFinal?: Date | null,
    public materialRecebido?: string | null,
    public acondicionamento?: string | null,
    public condicaoMaterial?: string | null,
    public status?: string | null,
    public tipoMedVet?: string | null,
    public valorTotal?: number | null,
    public tipoPagamento?: string | null,
    public situacao?: string | null,
    public users?: IUser[] | null,
    public midias?: IMidia[] | null,
    public exames?: IExame[] | null,
    public propriedade?: IPropriedade | null,
    public medicoveterinario?: IMedicoveterinario | null
  ) {}
}
