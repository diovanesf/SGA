<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="viewForm" role="form">
        <h2>
          Laudo de Exame Virológico <br/><br/>
          {{ amostra.protocolo }}
        </h2>
        <div class="text-right">
          <button class="btn btn-success" v-on:click="downloadPDF()">
                    <span>
                        Download PDF
                    </span>
          </button>
        </div>
        <div>
          <hr/>
          <div class="form-group">
            <b>Dados do cliente</b>
          </div>
          <hr/>
          <div class="form-group">
            <label class="form-control-label" for="proprietario-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="proprietario-nome"
              id="proprietario-nome"
              data-cy="proprietario-nome"
              v-model="amostra.propriedade.proprietario.nome"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-endereco">Endereço</label>
            <input
              type="text"
              class="form-control"
              name="propriedade-endereco"
              id="propriedade-endereco"
              data-cy="propriedade-endereco"
              v-model="amostra.propriedade.endereco.endereco"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-cidade">Cidade</label>
            <input
              type="text"
              class="form-control"
              name="propriedade-cidade"
              id="propriedade-cidade"
              data-cy="propriedade-cidade"
              v-model="amostra.propriedade.endereco.cidade"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proprietario-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="proprietario-email"
              id="proprietario-email"
              data-cy="proprietario-email"
              v-model="amostra.propriedade.proprietario.email"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proprietario-telefone">Fone</label>
            <input
              type="text"
              class="form-control"
              name="proprietario-telefone"
              id="proprietario-telefone"
              data-cy="proprietario-telefone"
              v-model="amostra.propriedade.proprietario.telefone"
              readonly
            />
          </div>
          <hr/>
          <div class="form-group">
            <b>Identificação da Amostra</b>
          </div>
          <hr/>
          <div class="form-group">
            <label class="form-control-label" for="amostra-materialRecebido">Material Recebido:</label>
            <input
              type="text"
              class="form-control"
              name="amostra-materialRecebido"
              id="amostra-materialRecebido"
              data-cy="amostra-materialRecebido"
              v-model="amostra.materialRecebido"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-especie">Espécie:</label>
            <input
              type="text"
              class="form-control"
              name="amostra-especie"
              id="amostra-especie"
              data-cy="amostra-especie"
              v-model="amostra.especie"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-acondicionamento">Acondicionamento:</label>
            <input
              type="text"
              class="form-control"
              name="amostra-acondicionamento"
              id="amostra-acondicionamento"
              data-cy="amostra-acondicionamento"
              v-model="amostra.acondicionamento"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-condicaoMaterial">Condição do Material:</label>
            <input
              type="text"
              class="form-control"
              name="amostra-condicaoMaterial"
              id="amostra-condicaoMaterial"
              data-cy="amostra-condicaoMaterial"
              v-model="amostra.condicaoMaterial"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-data">Data:</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="amostra-dataInicial"
                  v-model="amostra.dataInicial"
                  name="dataInicial"
                  class="form-control"
                  button-only
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="amostra-dataInicial"
                data-cy="dataInicial"
                type="text"
                class="form-control"
                name="dataInicial"
                v-model="amostra.dataInicial"
                readonly
              />
            </b-input-group>
          </div>
        </div>
        <hr/>
        <div class="form-group">
          <b>Exames e seus resultados</b>
        </div>
        <div class="table-responsive">
          <table class="table table-striped" aria-describedby="exames">
            <thead>
            <tr>
              <th scope="row"><span>Nome</span></th>
              <th scope="row"><span>Tipo</span></th>
              <th scope="row"><span>Resultado</span></th>
              <th scope="row"><span>Amostra</span></th>
              <th scope="row"><span>Observacoes</span></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="exame in exames" :key="exame.id" data-cy="entityTable">
              <td>{{ exame.nome }}</td>
              <td>{{ exame.tipo }}</td>
              <td>{{ exame.resultado }}</td>
              <td>{{ exame.subamostra.subAmostra }}</td>
              <td>{{ exame.observacoes }}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Back</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./laudo.component.ts"></script>
