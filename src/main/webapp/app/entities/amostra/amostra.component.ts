import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAmostra } from '@/shared/model/amostra.model';

import AmostraService from './amostra.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Amostra extends Vue {
  @Inject('amostraService') private amostraService: () => AmostraService;
  private removeId: number = null;

  public amostras: IAmostra[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAmostras();
  }

  public clear(): void {
    this.retrieveAllAmostras();
  }

  public retrieveAllAmostras(): void {
    this.isFetching = true;

    this.amostraService()
      .retrieve()
      .then(
        res => {
          this.amostras = res.data;
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

  public prepareRemove(instance: IAmostra): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAmostra(): void {
    this.amostraService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Amostra is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAmostras();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
