<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.exame.home.createOrEditLabel" data-cy="ExameCreateUpdateHeading">Create or edit a Exame</h2>
        <div>
          <div class="form-group" v-if="exame.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="exame.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="exame-nome"
              data-cy="nome"
              :class="{ valid: !$v.exame.nome.$invalid, invalid: $v.exame.nome.$invalid }"
              v-model="$v.exame.nome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-tipo">Tipo</label>
            <input
              type="text"
              class="form-control"
              name="tipo"
              id="exame-tipo"
              data-cy="tipo"
              :class="{ valid: !$v.exame.tipo.$invalid, invalid: $v.exame.tipo.$invalid }"
              v-model="$v.exame.tipo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-resultado">Resultado</label>
            <input
              type="text"
              class="form-control"
              name="resultado"
              id="exame-resultado"
              data-cy="resultado"
              :class="{ valid: !$v.exame.resultado.$invalid, invalid: $v.exame.resultado.$invalid }"
              v-model="$v.exame.resultado.$model"
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
            :disabled="$v.exame.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./exame-update.component.ts"></script>
