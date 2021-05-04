import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISubamostra } from '@/shared/model/subamostra.model';

import SubamostraService from './subamostra.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Subamostra extends Vue {
  @Inject('subamostraService') private subamostraService: () => SubamostraService;
  private removeId: number = null;

  public subamostras: ISubamostra[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSubamostras();
  }

  public clear(): void {
    this.retrieveAllSubamostras();
  }

  public retrieveAllSubamostras(): void {
    this.isFetching = true;

    this.subamostraService()
      .retrieve()
      .then(
        res => {
          this.subamostras = res.data;
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

  public prepareRemove(instance: ISubamostra): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSubamostra(): void {
    this.subamostraService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Subamostra is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSubamostras();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
