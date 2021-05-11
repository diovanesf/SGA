import {mixins} from 'vue-class-component';

import {Component, Vue, Inject} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import {IAmostra} from '@/shared/model/amostra.model';

import AmostraService from '@/entities/amostra/amostra.service';
import ExameService from '@/entities/exame/exame.service';
import PropriedadeService from '@/entities/propriedade/propriedade.service';
import ProprietarioService from '@/entities/proprietario/proprietario.service';
import {IExame} from '@/shared/model/exame.model';
import jsPDF from 'jspdf';
import "jspdf-autotable";

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Laudo extends Vue {
  @Inject('amostraService') private amostraService: () => AmostraService;

  @Inject('exameService') private exameService: () => ExameService;

  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;

  @Inject('proprietarioService') private proprietarioService: () => ProprietarioService;

  public amostra: IAmostra = {};
  public exames: IExame[] = [];
  public enderecoId: number = null;
  public amostraId: number = null;
  public isFetching = false;
  public cont: number = 250;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amostraId) {
        vm.amostraId = to.params.amostraId;
        vm.retrieveAmostra(to.params.amostraId);
        vm.retrieveExameByAmostra(to.params.amostraId);
      }
    });
  }

  public retrieveAmostra(amostraId: number): void {
    this.isFetching = true;
    this.amostraService()
      .find(amostraId)
      .then(res => {
        this.amostra = res;
        // console.log(this.amostra);
        this.retrievePropriedade(this.amostra.propriedade.id);
        this.isFetching = false;
      });
  }

  public retrieveExameByAmostra(amostraId: number): void {
    this.exameService()
      .retrieveByAmostra(amostraId)
      .then(res => {
        this.exames = res.data;
      });
  }

  public retrievePropriedade(propriedadeId: number): void {
    this.propriedadeService()
      .find(propriedadeId)
      .then(res => {
        this.amostra.propriedade = res;
        console.log(this.amostra.propriedade);
        this.retrieveProprietario(this.amostra.propriedade.proprietario.id);
      });
  }

  public retrieveProprietario(proprietarioId: number): void {
    this.proprietarioService()
      .find(proprietarioId)
      .then(res => {
        this.amostra.propriedade.proprietario = res;
      });
  }

  public previousState(): void {
    this.$router.push('/Amostra');
  }

  public downloadPDF(): void {

    let dataAtual = new Date();

    const doc = new jsPDF();
    // doc.setLineWidth(20);
    // doc.lines([[20,20],[-2,2],[1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9],[2,1]], 212,110, 20);
    doc.setFont("times", "normal");
    doc.setFontSize(18);
    doc.text('Laboratório de Virologia', 105, 10, null, null, "center");
    doc.setFontSize(14);
    doc.setFont("times", "normal");
    doc.text('Curso de Medicina Veterinária',105, 15, null, null, "center");
    doc.setFont("times", "normal");
    doc.text('Casa 4, Campus Uruguaiana, UNIPAMPA',105, 20, null, null, "center");
    doc.setFont("times", "normal");
    doc.text('BR 472, km 592, CEP 97.500-008. Uruguaiana, RS',105, 25, null, null, "center");
    doc.setFont("times", "normal");
    doc.text('email para contato: mario.brum@uniapampa.edu.br',105, 30, null, null, "center");


    doc.setFont("times", "normal");
    doc.setFontSize(18);
    doc.text('Laudo de Exame Virológico '+ this.amostra.protocolo, 105, 50, null, null, "center");
    doc.setFontSize(16);
    doc.setFont("helvetica", "bold");
    doc.text('Dados do cliente:'+ this.amostra.propriedade.proprietario.nome,10,80);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Nome: '+ this.amostra.propriedade.proprietario.nome,15,90);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Endereço: '+ this.amostra.propriedade.endereco.endereco,15,100);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Cidade: '+ this.amostra.propriedade.endereco.cidade,15,110);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Email: '+ this.amostra.propriedade.proprietario.email,15,120);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Fone: '+ this.amostra.propriedade.proprietario.telefone,15,130);
    doc.setFontSize(18);
    doc.setFont("helvetica", "bold");
    doc.text('Identificação da Amostra',10,145);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Material Recebido: '+ this.amostra.materialRecebido,15,155);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Espécie: '+ this.amostra.especie,15,165);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Acondicionamento: '+ this.amostra.acondicionamento,15,175);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Condição do Material: '+ this.amostra.condicaoMaterial,15,185);
    doc.setFontSize(16);
    doc.setFont("times", "normal");
    doc.text('Data de Recebimento: '+ this.amostra.dataInicial,15,195);
    // doc.setFontSize(16);
    // doc.setFont("times", "normal");
    // doc.text('Data de Emissão do Laudo: '+ dataAtual.getFullYear() + '-' + dataAtual.getMonth() + '-' + dataAtual.getDate(),110,195);

    doc.setFontSize(18);
    doc.setFont("helvetica", "bold");
    doc.text('Exames e seus resultados',10,220);

    doc.setFontSize(14);
    doc.setFont("times", "bold");
    doc.text('Nome', 10, 235);
    doc.setFontSize(14);
    doc.setFont("times", "bold");
    doc.text('Suspeita', 110, 235);
    doc.setFontSize(14);
    doc.setFont("times", "bold");
    doc.text('Resultado', 140, 235);
    doc.setFontSize(14);
    doc.setFont("times", "bold");
    doc.text('Amostra', 180, 235);
    doc.setFont("times", "bold");

      for(let i = 0; i < this.exames.length; i++){

      doc.setFontSize(12);
      doc.text(this.exames[i].nome, 10, this.cont);
      doc.setFontSize(12);
      doc.text(this.exames[i].tipo, 110, this.cont);
      doc.setFontSize(12);
      doc.text(this.exames[i].resultado, 140, this.cont);
      doc.setFontSize(12);
      doc.text(this.exames[i].subamostra.subAmostra, 180, this.cont);
      this.cont = this.cont + 10;

      if(i/3 == 0){
        doc.addPage( "landscape");
        this.cont = 20;
      }

      }

    doc.setFont("times", "bold");
    doc.text("Responsável:", 20, 290);

    doc.setFont("times", "bold");
    doc.text("CRMV:", 140, 290);


    doc.line(55, 290, 130, 290); // horizontal line

    doc.save("Laudo_"+this.amostra.protocolo+".pdf");

    this.$router.push('/Amostra');
  }
}
