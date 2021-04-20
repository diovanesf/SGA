import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMedicoveterinario } from '@/shared/model/medicoveterinario.model';

import MedicoveterinarioService from './medicoveterinario.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Medicoveterinario extends Vue {
  @Inject('medicoveterinarioService') private medicoveterinarioService: () => MedicoveterinarioService;
  private removeId: number = null;

  public medicoveterinarios: IMedicoveterinario[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMedicoveterinarios();
  }

  public clear(): void {
    this.retrieveAllMedicoveterinarios();
  }

  public retrieveAllMedicoveterinarios(): void {
    this.isFetching = true;

    this.medicoveterinarioService()
      .retrieve()
      .then(
        res => {
          this.medicoveterinarios = res.data;
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

  public prepareRemove(instance: IMedicoveterinario): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMedicoveterinario(): void {
    this.medicoveterinarioService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Medicoveterinario is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMedicoveterinarios();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
