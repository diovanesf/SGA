<template>
  <div>
    <h2 id="page-heading" data-cy="SubamostraHeading">
      <span id="subamostra-heading">Subamostras</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'SubamostraCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-subamostra"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Subamostra </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && subamostras && subamostras.length === 0">
      <span>No subamostras found</span>
    </div>
    <div class="table-responsive" v-if="subamostras && subamostras.length > 0">
      <table class="table table-striped" aria-describedby="subamostras">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Sub Amostra</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="subamostra in subamostras" :key="subamostra.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SubamostraView', params: { subamostraId: subamostra.id } }">{{ subamostra.id }}</router-link>
            </td>
            <td>{{ subamostra.subAmostra }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SubamostraView', params: { subamostraId: subamostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SubamostraEdit', params: { subamostraId: subamostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(subamostra)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="rp6App.subamostra.delete.question" data-cy="subamostraDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-subamostra-heading">Are you sure you want to delete this Subamostra?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-subamostra"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeSubamostra()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./subamostra.component.ts"></script>
