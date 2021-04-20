<template>
  <div>
    <h2 id="page-heading" data-cy="AmostraHeading">
      <span id="amostra-heading">Amostras</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'AmostraCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-amostra"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Amostra </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && amostras && amostras.length === 0">
      <span>No amostras found</span>
    </div>
    <div class="table-responsive" v-if="amostras && amostras.length > 0">
      <table class="table table-striped" aria-describedby="amostras">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Protocolo</span></th>
            <th scope="row"><span>Forma Envio</span></th>
            <th scope="row"><span>Numero Amostras</span></th>
            <th scope="row"><span>Especie</span></th>
            <th scope="row"><span>Data Inicial</span></th>
            <th scope="row"><span>Data Final</span></th>
            <th scope="row"><span>Material Recebido</span></th>
            <th scope="row"><span>Acondicionamento</span></th>
            <th scope="row"><span>Condicao Material</span></th>
            <th scope="row"><span>Status</span></th>
            <th scope="row"><span>Tipo Med Vet</span></th>
            <th scope="row"><span>Valor Total</span></th>
            <th scope="row"><span>Tipo Pagamento</span></th>
            <th scope="row"><span>Situacao</span></th>
            <th scope="row"><span>User</span></th>
            <th scope="row"><span>Propriedade</span></th>
            <th scope="row"><span>Medicoveterinario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="amostra in amostras" :key="amostra.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AmostraView', params: { amostraId: amostra.id } }">{{ amostra.id }}</router-link>
            </td>
            <td>{{ amostra.protocolo }}</td>
            <td>{{ amostra.formaEnvio }}</td>
            <td>{{ amostra.numeroAmostras }}</td>
            <td>{{ amostra.especie }}</td>
            <td>{{ amostra.dataInicial }}</td>
            <td>{{ amostra.dataFinal }}</td>
            <td>{{ amostra.materialRecebido }}</td>
            <td>{{ amostra.acondicionamento }}</td>
            <td>{{ amostra.condicaoMaterial }}</td>
            <td>{{ amostra.status }}</td>
            <td>{{ amostra.tipoMedVet }}</td>
            <td>{{ amostra.valorTotal }}</td>
            <td>{{ amostra.tipoPagamento }}</td>
            <td>{{ amostra.situacao }}</td>
            <td>
              <span v-for="(user, i) in amostra.users" :key="user.id"
                >{{ i > 0 ? ', ' : '' }}
                {{ user.login }}
              </span>
            </td>
            <td>
              <div v-if="amostra.propriedade">
                <router-link :to="{ name: 'PropriedadeView', params: { propriedadeId: amostra.propriedade.id } }">{{
                  amostra.propriedade.tipoPropriedade
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="amostra.medicoveterinario">
                <router-link :to="{ name: 'MedicoveterinarioView', params: { medicoveterinarioId: amostra.medicoveterinario.id } }">{{
                  amostra.medicoveterinario.nome
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AmostraView', params: { amostraId: amostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AmostraEdit', params: { amostraId: amostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(amostra)"
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
        ><span id="rp6App.amostra.delete.question" data-cy="amostraDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-amostra-heading">Are you sure you want to delete this Amostra?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-amostra"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAmostra()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./amostra.component.ts"></script>
