import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVacina } from '@/shared/model/vacina.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import VacinaService from './vacina.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Vacina extends mixins(JhiDataUtils) {
  @Inject('vacinaService') private vacinaService: () => VacinaService;
  private removeId: number = null;

  public vacinas: IVacina[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVacinas();
  }

  public clear(): void {
    this.retrieveAllVacinas();
  }

  public retrieveAllVacinas(): void {
    this.isFetching = true;

    this.vacinaService()
      .retrieve()
      .then(
        res => {
          this.vacinas = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IVacina): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVacina(): void {
    this.vacinaService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Vacina is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVacinas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
