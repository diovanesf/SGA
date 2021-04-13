import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMidia } from '@/shared/model/midia.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import MidiaService from './midia.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Midia extends mixins(JhiDataUtils) {
  @Inject('midiaService') private midiaService: () => MidiaService;
  private removeId: number = null;

  public midias: IMidia[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMidias();
  }

  public clear(): void {
    this.retrieveAllMidias();
  }

  public retrieveAllMidias(): void {
    this.isFetching = true;

    this.midiaService()
      .retrieve()
      .then(
        res => {
          this.midias = res.data;
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

  public prepareRemove(instance: IMidia): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMidia(): void {
    this.midiaService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Midia is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMidias();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
