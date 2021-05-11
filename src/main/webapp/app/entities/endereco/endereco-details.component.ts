import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEndereco } from '@/shared/model/endereco.model';
import EnderecoService from './endereco.service';

@Component
export default class EnderecoDetails extends Vue {
  @Inject('enderecoService') private enderecoService: () => EnderecoService;
  public endereco: IEndereco = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.enderecoId) {
        vm.retrieveEndereco(to.params.enderecoId);
      }
    });
  }

  public retrieveEndereco(enderecoId) {
    this.enderecoService()
      .find(enderecoId)
      .then(res => {
        this.endereco = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
