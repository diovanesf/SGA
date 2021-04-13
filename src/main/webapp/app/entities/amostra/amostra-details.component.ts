import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAmostra } from '@/shared/model/amostra.model';
import AmostraService from './amostra.service';

@Component
export default class AmostraDetails extends Vue {
  @Inject('amostraService') private amostraService: () => AmostraService;
  public amostra: IAmostra = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amostraId) {
        vm.retrieveAmostra(to.params.amostraId);
      }
    });
  }

  public retrieveAmostra(amostraId) {
    this.amostraService()
      .find(amostraId)
      .then(res => {
        this.amostra = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
