<template>
  <div>
    <h2 id="page-heading" data-cy="MedicoveterinarioHeading">
      <span id="medicoveterinario-heading">Médico Veterinário</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-outline-success mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Atualizar lista</span>
        </button>
        <router-link :to="{ name: 'MedicoveterinarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-outline-success jh-create-entity create-medicoveterinario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Criar um novo Médico Veterinário </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && medicoveterinarios && medicoveterinarios.length === 0">
      <span>Nenhum Médico Veterinário encontrado</span>
    </div>
    <div class="table-responsive" v-if="medicoveterinarios && medicoveterinarios.length > 0">
      <table class="table table-striped" aria-describedby="medicoveterinarios">
        <thead>
          <tr>
            <!-- <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th> -->
            <th scope="row" v-on:click="changeOrder('nome')">
              <span>Nome</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nome'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('telefone')">
              <span>Telefone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'telefone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('email')">
              <span>E-mail</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('crmv')">
              <span>CRMV</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'crmv'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('enviarLaudo')">
              <span>Enviar Laudo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'enviarLaudo'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="medicoveterinario in medicoveterinarios" :key="medicoveterinario.id" data-cy="entityTable">
            <!-- <td>
              <router-link :to="{ name: 'MedicoveterinarioView', params: { medicoveterinarioId: medicoveterinario.id } }">{{
                medicoveterinario.id
              }}</router-link>
            </td> -->
            <td>{{ medicoveterinario.nome }}</td>
            <td>{{ medicoveterinario.telefone }}</td>
            <td>{{ medicoveterinario.email }}</td>
            <td>{{ medicoveterinario.crmv }}</td>
            <td>{{ medicoveterinario.enviarLaudo }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MedicoveterinarioView', params: { medicoveterinarioId: medicoveterinario.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-success btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Ver</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'MedicoveterinarioEdit', params: { medicoveterinarioId: medicoveterinario.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editar</span>
                  </button>
                </router-link>
                <b-button
                  v-if="verificaUsuario()"
                  v-on:click="prepareRemove(medicoveterinario)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Deletar</span>
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
          >Confirmação de exclusão</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-medicoveterinario-heading">Você tem certeza que deseja deletar este Médico Veterinário?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancelar</button>
        <button
          type="button"
          class="btn btn-danger"
          id="jhi-confirm-delete-medicoveterinario"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeMedicoveterinario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="medicoveterinarios && medicoveterinarios.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./medicoveterinario.component.ts"></script>
