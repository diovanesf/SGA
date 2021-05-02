import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISubamostra } from '@/shared/model/subamostra.model';
import SubamostraService from './subamostra.service';

@Component
export default class SubamostraDetails extends Vue {
  @Inject('subamostraService') private subamostraService: () => SubamostraService;
  public subamostra: ISubamostra = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subamostraId) {
        vm.retrieveSubamostra(to.params.subamostraId);
      }
    });
  }

  public retrieveSubamostra(subamostraId) {
    this.subamostraService()
      .find(subamostraId)
      .then(res => {
        this.subamostra = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
