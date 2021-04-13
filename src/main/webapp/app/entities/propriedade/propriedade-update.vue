<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.propriedade.home.createOrEditLabel" data-cy="PropriedadeCreateUpdateHeading">Create or edit a Propriedade</h2>
        <div>
          <div class="form-group" v-if="propriedade.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="propriedade.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-tipo">Tipo</label>
            <input
              type="text"
              class="form-control"
              name="tipo"
              id="propriedade-tipo"
              data-cy="tipo"
              :class="{ valid: !$v.propriedade.tipo.$invalid, invalid: $v.propriedade.tipo.$invalid }"
              v-model="$v.propriedade.tipo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-numeroAnimais">Numero Animais</label>
            <input
              type="number"
              class="form-control"
              name="numeroAnimais"
              id="propriedade-numeroAnimais"
              data-cy="numeroAnimais"
              :class="{ valid: !$v.propriedade.numeroAnimais.$invalid, invalid: $v.propriedade.numeroAnimais.$invalid }"
              v-model.number="$v.propriedade.numeroAnimais.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-acometidos">Acometidos</label>
            <input
              type="text"
              class="form-control"
              name="acometidos"
              id="propriedade-acometidos"
              data-cy="acometidos"
              :class="{ valid: !$v.propriedade.acometidos.$invalid, invalid: $v.propriedade.acometidos.$invalid }"
              v-model="$v.propriedade.acometidos.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-observacoes">Observacoes</label>
            <input
              type="text"
              class="form-control"
              name="observacoes"
              id="propriedade-observacoes"
              data-cy="observacoes"
              :class="{ valid: !$v.propriedade.observacoes.$invalid, invalid: $v.propriedade.observacoes.$invalid }"
              v-model="$v.propriedade.observacoes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-pricipalSuspeita">Pricipal Suspeita</label>
            <input
              type="text"
              class="form-control"
              name="pricipalSuspeita"
              id="propriedade-pricipalSuspeita"
              data-cy="pricipalSuspeita"
              :class="{ valid: !$v.propriedade.pricipalSuspeita.$invalid, invalid: $v.propriedade.pricipalSuspeita.$invalid }"
              v-model="$v.propriedade.pricipalSuspeita.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="propriedade-endereco">Endereco</label>
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
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.propriedade.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./propriedade-update.component.ts"></script>
