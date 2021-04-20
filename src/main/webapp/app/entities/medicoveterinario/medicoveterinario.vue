<template>
  <div>
    <h2 id="page-heading" data-cy="MedicoveterinarioHeading">
      <span id="medicoveterinario-heading">Medicoveterinarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'MedicoveterinarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-medicoveterinario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Medicoveterinario </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && medicoveterinarios && medicoveterinarios.length === 0">
      <span>No medicoveterinarios found</span>
    </div>
    <div class="table-responsive" v-if="medicoveterinarios && medicoveterinarios.length > 0">
      <table class="table table-striped" aria-describedby="medicoveterinarios">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Nome</span></th>
            <th scope="row"><span>Telefone</span></th>
            <th scope="row"><span>Email</span></th>
            <th scope="row"><span>CRMV</span></th>
            <th scope="row"><span>Enviar Laudo</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="medicoveterinario in medicoveterinarios" :key="medicoveterinario.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MedicoveterinarioView', params: { medicoveterinarioId: medicoveterinario.id } }">{{
                medicoveterinario.id
              }}</router-link>
            </td>
            <td>{{ medicoveterinario.nome }}</td>
            <td>{{ medicoveterinario.telefone }}</td>
            <td>{{ medicoveterinario.email }}</td>
            <td>{{ medicoveterinario.CRMV }}</td>
            <td>{{ medicoveterinario.enviarLaudo }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MedicoveterinarioView', params: { medicoveterinarioId: medicoveterinario.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'MedicoveterinarioEdit', params: { medicoveterinarioId: medicoveterinario.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(medicoveterinario)"
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
        ><span id="rp6App.medicoveterinario.delete.question" data-cy="medicoveterinarioDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-medicoveterinario-heading">Are you sure you want to delete this Medicoveterinario?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-medicoveterinario"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeMedicoveterinario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./medicoveterinario.component.ts"></script>
