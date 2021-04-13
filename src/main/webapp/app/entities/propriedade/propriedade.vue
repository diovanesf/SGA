<template>
  <div>
    <h2 id="page-heading" data-cy="PropriedadeHeading">
      <span id="propriedade-heading">Propriedades</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PropriedadeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-propriedade"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Propriedade </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && propriedades && propriedades.length === 0">
      <span>No propriedades found</span>
    </div>
    <div class="table-responsive" v-if="propriedades && propriedades.length > 0">
      <table class="table table-striped" aria-describedby="propriedades">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Tipo</span></th>
            <th scope="row"><span>Numero Animais</span></th>
            <th scope="row"><span>Acometidos</span></th>
            <th scope="row"><span>Observacoes</span></th>
            <th scope="row"><span>Pricipal Suspeita</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="propriedade in propriedades" :key="propriedade.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PropriedadeView', params: { propriedadeId: propriedade.id } }">{{ propriedade.id }}</router-link>
            </td>
            <td>{{ propriedade.tipo }}</td>
            <td>{{ propriedade.numeroAnimais }}</td>
            <td>{{ propriedade.acometidos }}</td>
            <td>{{ propriedade.observacoes }}</td>
            <td>{{ propriedade.pricipalSuspeita }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PropriedadeView', params: { propriedadeId: propriedade.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PropriedadeEdit', params: { propriedadeId: propriedade.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(propriedade)"
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
        ><span id="rp6App.propriedade.delete.question" data-cy="propriedadeDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-propriedade-heading">Are you sure you want to delete this Propriedade?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-propriedade"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePropriedade()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./propriedade.component.ts"></script>
