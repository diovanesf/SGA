<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.subamostra.home.createOrEditLabel" data-cy="SubamostraCreateUpdateHeading">Create or edit a Subamostra</h2>
        <div>
          <div class="form-group" v-if="subamostra.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="subamostra.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="subamostra-subAmostra">Sub Amostra</label>
            <input
              type="text"
              class="form-control"
              name="subAmostra"
              id="subamostra-subAmostra"
              data-cy="subAmostra"
              :class="{ valid: !$v.subamostra.subAmostra.$invalid, invalid: $v.subamostra.subAmostra.$invalid }"
              v-model="$v.subamostra.subAmostra.$model"
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
            :disabled="$v.subamostra.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./subamostra-update.component.ts"></script>
