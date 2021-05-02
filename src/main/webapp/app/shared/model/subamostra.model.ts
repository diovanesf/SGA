export interface ISubamostra {
  id?: number;
  subAmostra?: string | null;
}

export class Subamostra implements ISubamostra {
  constructor(public id?: number, public subAmostra?: string | null) {}
}
