import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPropriedade } from '@/shared/model/propriedade.model';

import PropriedadeService from './propriedade.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Propriedade extends Vue {
  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;
  private removeId: number = null;

  public propriedades: IPropriedade[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPropriedades();
  }

  public clear(): void {
    this.retrieveAllPropriedades();
  }

  public retrieveAllPropriedades(): void {
    this.isFetching = true;

    this.propriedadeService()
      .retrieve()
      .then(
        res => {
          this.propriedades = res.data;
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

  public prepareRemove(instance: IPropriedade): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePropriedade(): void {
    this.propriedadeService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Propriedade is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPropriedades();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
