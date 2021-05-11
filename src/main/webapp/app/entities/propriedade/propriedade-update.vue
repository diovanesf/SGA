<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" validate v-on:submit.prevent="save()">
        <h2 id="rp6App.propriedade.home.createOrEditLabel" data-cy="PropriedadeCreateUpdateHeading">Criar ou editar uma Propriedade</h2>
        <div>
          <div class="form-group" v-if="propriedade.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="propriedade.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-tipoPropriedade">Tipo de Propriedade</label>
            <select
              class="form-control"
              id="propriedade-tipoPropriedade"
              data-cy="tipoPropriedade"
              name="tipoPropriedade"
              v-model="propriedade.tipoPropriedade"
              v-on:click="setTipoPropriedade()"
            >
              <option value="RURAL_HARAS_GRANJA">Rural/Haras/Granja</option>
              <option value="CANIL_GATIL">Canil/Gatil</option>
            </select>
          </div>
          <div class="form-group" v-if="propriedade.tipoPropriedade === 'RURAL_HARAS_GRANJA'">
            <label class="form-control-label" for="propriedade-tipoCriacao">Tipo de Criação</label>
            <select
              class="form-control"
              id="propriedade-tipoCriacao"
              data-cy="tipoCriacao"
              name="tipoCriacao"
              v-model="propriedade.tipoCriacao"
            >
              <option value="CRIACAO_INTENSIVA">Criação intensiva</option>
              <option value="CRIACAO_EXTENSIVA">Criação extensiva</option>
              <option value="CRIACAO_SEMIEXTENSIVA">Criação semiextensiva</option>
              <option value="ABERTA">Aberta</option>
              <option value="FECHADA">Fechada</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-proprietario">Proprietário</label>
            <select
              class="form-control"
              id="propriedade-proprietario"
              data-cy="proprietario"
              name="proprietario"
              v-model="propriedade.proprietario"
              required="required"
            >
<!--              <option v-bind:value="null"></option>-->
              <option
                v-bind:value="
                  propriedade.proprietario && proprietarioOption.id === propriedade.proprietario.id
                    ? propriedade.proprietario
                    : proprietarioOption
                "
                v-for="proprietarioOption in proprietarios"
                :key="proprietarioOption.id"
              >
                {{ proprietarioOption.nome }}
              </option>
            </select>
          </div>
          <!-- <div class="form-group">
            <label class="form-control-label" for="propriedade-endereco">Endereço</label>
            <select class="form-control" id="propriedade-endereco" data-cy="endereco" name="endereco" v-model="propriedade.endereco">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="propriedade.endereco && enderecoOption.id === propriedade.endereco.id ? propriedade.endereco : enderecoOption"
                v-for="enderecoOption in enderecos"
                :key="enderecoOption.id"
              >
                {{ enderecoOption.endereco }}
              </option>
            </select>
          </div> -->
          <div class="form-group">
            <label class="form-control-label" for="endereco-endereco">Endereço</label>
            <input type="text" class="form-control" name="endereco" id="endereco-endereco" data-cy="endereco" v-model="propriedade.endereco.endereco" />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="endereco-cep">CEP</label>
            <input type="text" class="form-control" name="cep" id="endereco-cep" data-cy="cep" v-model="propriedade.endereco.cep" />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="endereco-cidade">Cidade</label>
            <input type="text" class="form-control" name="cidade" id="endereco-cidade" data-cy="cidade" v-model="propriedade.endereco.cidade" />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="endereco-estado">Estado</label>
            <input type="text" class="form-control" name="estado" id="endereco-estado" data-cy="estado" v-model="propriedade.endereco.estado" />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="endereco-coordenadasGps">Coordenadas Gps</label>
            <input
              type="text"
              class="form-control"
              name="coordenadasGps"
              id="endereco-coordenadasGps"
              data-cy="coordenadasGps"
              v-model="propriedade.endereco.coordenadasGps"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-outline-danger" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancelar</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.propriedade.$invalid || isSaving"
            class="btn btn-success"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Salvar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./propriedade-update.component.ts"></script>
