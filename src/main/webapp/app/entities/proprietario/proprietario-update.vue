<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.proprietario.home.createOrEditLabel" data-cy="ProprietarioCreateUpdateHeading">Criar ou editar um Proprietário</h2>
        <div>
          <!-- <div class="form-group" v-if="proprietario.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="proprietario.id" readonly />
          </div> -->
          <div class="form-group">
            <label class="form-control-label" for="proprietario-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="proprietario-nome"
              data-cy="nome"
              :class="{ valid: !$v.proprietario.nome.$invalid, invalid: $v.proprietario.nome.$invalid }"
              v-model="$v.proprietario.nome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proprietario-telefone">Telefone</label>
            <input
              type="text"
              class="form-control"
              name="telefone"
              id="proprietario-telefone"
              data-cy="telefone"
              :class="{ valid: !$v.proprietario.telefone.$invalid, invalid: $v.proprietario.telefone.$invalid }"
              v-model="$v.proprietario.telefone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proprietario-email">E-mail</label>
            <input
              type="email"
              class="form-control"
              name="email"
              id="proprietario-email"
              data-cy="email"
              required
              :class="{ valid: !$v.proprietario.email.$invalid, invalid: $v.proprietario.email.$invalid }"
              v-model="$v.proprietario.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proprietario-enviarLaudo">Enviar Laudo</label>
            <input
              type="checkbox"
              class="form-check"
              name="enviarLaudo"
              id="proprietario-enviarLaudo"
              data-cy="enviarLaudo"
              :class="{ valid: !$v.proprietario.enviarLaudo.$invalid, invalid: $v.proprietario.enviarLaudo.$invalid }"
              v-model="$v.proprietario.enviarLaudo.$model"
            />
          </div>
          <!-- <div class="form-group">
            <label class="form-control-label" for="proprietario-propriedade">Propriedade</label>
            <select
              class="form-control"
              id="proprietario-propriedade"
              data-cy="propriedade"
              name="propriedade"
              v-model="proprietario.propriedade"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  proprietario.propriedade && propriedadeOption.id === proprietario.propriedade.id
                    ? proprietario.propriedade
                    : propriedadeOption
                "
                v-for="propriedadeOption in propriedades"
                :key="propriedadeOption.id"
              >
                {{ propriedadeOption.tipoPropriedade }}
              </option>
            </select>
          </div> -->
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-outline-danger" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancelar</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.proprietario.$invalid || isSaving"
            class="btn btn-success"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Salvar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./proprietario-update.component.ts"></script>
