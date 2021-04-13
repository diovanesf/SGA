import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProprietario } from '@/shared/model/proprietario.model';

import ProprietarioService from './proprietario.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Proprietario extends Vue {
  @Inject('proprietarioService') private proprietarioService: () => ProprietarioService;
  private removeId: number = null;

  public proprietarios: IProprietario[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProprietarios();
  }

  public clear(): void {
    this.retrieveAllProprietarios();
  }

  public retrieveAllProprietarios(): void {
    this.isFetching = true;

    this.proprietarioService()
      .retrieve()
      .then(
        res => {
          this.proprietarios = res.data;
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

  public prepareRemove(instance: IProprietario): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProprietario(): void {
    this.proprietarioService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Proprietario is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllProprietarios();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
