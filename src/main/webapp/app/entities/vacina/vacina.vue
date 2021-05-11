<template>
  <div>
    <h2 id="page-heading" data-cy="VacinaHeading">
      <span id="vacina-heading">Vacinas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-outline-success mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Atualizar Lista</span>
        </button>
        <router-link :to="{ name: 'VacinaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-outline-success jh-create-entity create-vacina"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Criar uma nova Vacina </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && vacinas && vacinas.length === 0">
      <span>Nenhuma vacina encontrada</span>
    </div>
    <div class="table-responsive" v-if="vacinas && vacinas.length > 0">
      <table class="table table-striped" aria-describedby="vacinas">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Status</span></th>
            <th scope="row"><span>Peso do Material</span></th>
            <th scope="row"><span>Estimativa Vacinas</span></th>
            <th scope="row"><span>Data de Conclusão</span></th>
            <th scope="row"><span>Observações</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vacina in vacinas" :key="vacina.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VacinaView', params: { vacinaId: vacina.id } }">{{ vacina.id }}</router-link>
            </td>
            <td>{{ vacina.status }}</td>
            <td>{{ vacina.pesoMaterial }}</td>
            <td>{{ vacina.estimativaVacinas }}</td>
            <td>{{ vacina.dataConclusao }}</td>
            <td>{{ vacina.observacoes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VacinaView', params: { vacinaId: vacina.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-outline-success btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Ver</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VacinaEdit', params: { vacinaId: vacina.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-warning btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editar</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(vacina)"
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
        ><span id="rp6App.vacina.delete.question" data-cy="vacinaDeleteDialogHeading">Confirmação de exclusão</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-vacina-heading">Você tem certeza que deseja deletar esta Vacina?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancelar</button>
        <button
          type="button"
          class="btn btn-danger"
          id="jhi-confirm-delete-vacina"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeVacina()"
        >
          Deletar
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./vacina.component.ts"></script>
