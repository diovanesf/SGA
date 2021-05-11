import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProprietario } from '@/shared/model/proprietario.model';
import ProprietarioService from './proprietario.service';

@Component
export default class ProprietarioDetails extends Vue {
  @Inject('proprietarioService') private proprietarioService: () => ProprietarioService;
  public proprietario: IProprietario = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proprietarioId) {
        vm.retrieveProprietario(to.params.proprietarioId);
      }
    });
  }

  public retrieveProprietario(proprietarioId) {
    this.proprietarioService()
      .find(proprietarioId)
      .then(res => {
        this.proprietario = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
