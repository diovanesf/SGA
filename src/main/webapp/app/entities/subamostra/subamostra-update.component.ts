import { Component, Vue, Inject } from 'vue-property-decorator';

import AmostraService from '@/entities/amostra/amostra.service';
import { IAmostra } from '@/shared/model/amostra.model';

import { ISubamostra, Subamostra } from '@/shared/model/subamostra.model';
import SubamostraService from './subamostra.service';

const validations: any = {
  subamostra: {
    subAmostra: {},
  },
};

@Component({
  validations,
})
export default class SubamostraUpdate extends Vue {
  @Inject('subamostraService') private subamostraService: () => SubamostraService;
  public subamostra: ISubamostra = new Subamostra();

  @Inject('amostraService') private amostraService: () => AmostraService;

  public amostras: IAmostra[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subamostraId) {
        vm.retrieveSubamostra(to.params.subamostraId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.subamostra.id) {
      this.subamostraService()
        .update(this.subamostra)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Subamostra is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.subamostraService()
        .create(this.subamostra)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Subamostra is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveSubamostra(subamostraId): void {
    this.subamostraService()
      .find(subamostraId)
      .then(res => {
        this.subamostra = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.amostraService()
      .retrieve()
      .then(res => {
        this.amostras = res.data;
      });
  }
}
