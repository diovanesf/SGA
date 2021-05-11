import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMedicoveterinario } from '@/shared/model/medicoveterinario.model';
import MedicoveterinarioService from './medicoveterinario.service';

@Component
export default class MedicoveterinarioDetails extends Vue {
  @Inject('medicoveterinarioService') private medicoveterinarioService: () => MedicoveterinarioService;
  public medicoveterinario: IMedicoveterinario = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.medicoveterinarioId) {
        vm.retrieveMedicoveterinario(to.params.medicoveterinarioId);
      }
    });
  }

  public retrieveMedicoveterinario(medicoveterinarioId) {
    this.medicoveterinarioService()
      .find(medicoveterinarioId)
      .then(res => {
        this.medicoveterinario = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
