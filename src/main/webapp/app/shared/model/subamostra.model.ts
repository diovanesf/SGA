import { IAmostra } from '@/shared/model/amostra.model';

export interface ISubamostra {
  id?: number;
  subAmostra?: string | null;
  amostra?: IAmostra | null;
}

export class Subamostra implements ISubamostra {
  constructor(public id?: number, public subAmostra?: string | null, public amostra?: IAmostra | null) {}
}
