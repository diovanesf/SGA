import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IExame } from '@/shared/model/exame.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ExameService from './exame.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Exame extends mixins(JhiDataUtils) {
  @Inject('exameService') private exameService: () => ExameService;
  private removeId: number = null;

  public exames: IExame[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllExames();
  }

  public clear(): void {
    this.retrieveAllExames();
  }

  public retrieveAllExames(): void {
    this.isFetching = true;

    this.exameService()
      .retrieve()
      .then(
        res => {
          this.exames = res.data;
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

  public prepareRemove(instance: IExame): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeExame(): void {
    this.exameService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Exame is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllExames();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
