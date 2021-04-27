<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.endereco.home.createOrEditLabel" data-cy="EnderecoCreateUpdateHeading">Criar ou editar um endereço</h2>
        <div>
          <!-- <div class="form-group" v-if="endereco.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="endereco.id" readonly />
          </div> -->
          <div class="form-group">
            <label class="form-control-label" for="endereco-endereco">Endereço</label>
            <input
              type="text"
              class="form-control"
              name="endereco"
              id="endereco-endereco"
              data-cy="endereco"
              :class="{ valid: !$v.endereco.endereco.$invalid, invalid: $v.endereco.endereco.$invalid }"
              v-model="$v.endereco.endereco.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="endereco-cep">CEP</label>
            <input
              type="text"
              class="form-control"
              name="cep"
              id="endereco-cep"
              data-cy="cep"
              :class="{ valid: !$v.endereco.cep.$invalid, invalid: $v.endereco.cep.$invalid }"
              v-model="$v.endereco.cep.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="endereco-cidade">Cidade</label>
            <input
              type="text"
              class="form-control"
              name="cidade"
              id="endereco-cidade"
              data-cy="cidade"
              :class="{ valid: !$v.endereco.cidade.$invalid, invalid: $v.endereco.cidade.$invalid }"
              v-model="$v.endereco.cidade.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="endereco-estado">Estado</label>
            <input
              type="text"
              class="form-control"
              name="estado"
              id="endereco-estado"
              data-cy="estado"
              :class="{ valid: !$v.endereco.estado.$invalid, invalid: $v.endereco.estado.$invalid }"
              v-model="$v.endereco.estado.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="endereco-coordenadasGps">Coordenadas GPS</label>
            <input
              type="text"
              class="form-control"
              name="coordenadasGps"
              id="endereco-coordenadasGps"
              data-cy="coordenadasGps"
              :class="{ valid: !$v.endereco.coordenadasGps.$invalid, invalid: $v.endereco.coordenadasGps.$invalid }"
              v-model="$v.endereco.coordenadasGps.$model"
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
            :disabled="$v.endereco.$invalid || isSaving"
            class="btn btn-success"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Salvar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./endereco-update.component.ts"></script>
