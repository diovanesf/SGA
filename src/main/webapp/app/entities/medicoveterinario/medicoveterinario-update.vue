<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" validate v-on:submit.prevent="save()">
        <h2 id="rp6App.medicoveterinario.home.createOrEditLabel" data-cy="MedicoveterinarioCreateUpdateHeading">
          Create or edit a Medicoveterinario
        </h2>
        <div>
          <!-- <div class="form-group" v-if="medicoveterinario.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="medicoveterinario.id" readonly />
          </div> -->
          <div class="form-group">
            <label class="form-control-label" for="medicoveterinario-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="medicoveterinario-nome"
              data-cy="nome"
              :class="{ valid: !$v.medicoveterinario.nome.$invalid, invalid: $v.medicoveterinario.nome.$invalid }"
              v-model="$v.medicoveterinario.nome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicoveterinario-telefone">Telefone</label>
            <input
              type="text"
              class="form-control"
              name="telefone"
              id="medicoveterinario-telefone"
              data-cy="telefone"
              :class="{ valid: !$v.medicoveterinario.telefone.$invalid, invalid: $v.medicoveterinario.telefone.$invalid }"
              v-model="$v.medicoveterinario.telefone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicoveterinario-email">Email</label>
            <input
              type="email"
              class="form-control"
              name="email"
              id="medicoveterinario-email"
              data-cy="email"
              required
              :class="{ valid: !$v.medicoveterinario.email.$invalid, invalid: $v.medicoveterinario.email.$invalid }"
              v-model="$v.medicoveterinario.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicoveterinario-crmv">CRMV</label>
            <input
              type="text"
              class="form-control"
              name="crmv"
              id="medicoveterinario-crmv"
              data-cy="crmv"
              :class="{ valid: !$v.medicoveterinario.crmv.$invalid, invalid: $v.medicoveterinario.crmv.$invalid }"
              v-model="$v.medicoveterinario.crmv.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="medicoveterinario-enviarLaudo">Enviar Laudo</label>
            <input
              type="checkbox"
              class="form-check"
              name="enviarLaudo"
              id="medicoveterinario-enviarLaudo"
              data-cy="enviarLaudo"
              :class="{ valid: !$v.medicoveterinario.enviarLaudo.$invalid, invalid: $v.medicoveterinario.enviarLaudo.$invalid }"
              v-model="$v.medicoveterinario.enviarLaudo.$model"
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
            :disabled="$v.medicoveterinario.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./medicoveterinario-update.component.ts"></script>
