<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.proprietario.home.createOrEditLabel" data-cy="ProprietarioCreateUpdateHeading">Create or edit a Proprietario</h2>
        <div>
          <div class="form-group" v-if="proprietario.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="proprietario.id" readonly />
          </div>
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
            <label class="form-control-label" for="proprietario-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="proprietario-email"
              data-cy="email"
              :class="{ valid: !$v.proprietario.email.$invalid, invalid: $v.proprietario.email.$invalid }"
              v-model="$v.proprietario.email.$model"
            />
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
            :disabled="$v.proprietario.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./proprietario-update.component.ts"></script>
