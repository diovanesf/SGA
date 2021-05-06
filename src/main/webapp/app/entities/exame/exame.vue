<template>
  <div>
    <h2 id="page-heading" data-cy="ExameHeading">
      <span id="exame-heading">Exames</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-outline-success mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Atualizar lista</span>
        </button>
        <router-link :to="{ name: 'ExameCreate', params: { amostraId: amostra.id } }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-outline-success jh-create-entity create-exame"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Adicionar um novo exame </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-success" v-if="!isFetching && exames && exames.length === 0">
      <span>Nenhum exame encontrado</span>
    </div>
    <div class="table-responsive" v-if="exames && exames.length > 0">
      <table class="table table-striped" aria-describedby="exames">
        <thead>
          <tr>
            <!-- <th scope="row"><span>ID</span></th> -->
            <th scope="row"><span>Nome</span></th>
            <th scope="row"><span>Tipo</span></th>
            <th scope="row"><span>Resultado</span></th>
            <th scope="row"><span>Data Teste</span></th>
            <th scope="row"><span>Data Leitura</span></th>
            <th scope="row"><span>Preenchimento Espelho</span></th>
            <th scope="row"><span>Observacoes</span></th>
            <th scope="row"><span>Valor</span></th>
            <th scope="row"><span>Amostra</span></th>
            <th scope="row"><span>Subamostra</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="exame in exames" :key="exame.id" data-cy="entityTable">
            <!-- <td>
            <router-link :to="{ name: 'ExameView', params: { exameId: exame.id } }">{{ exame.id }}</router-link>
          </td> -->
            <td>{{ exame.nome }}</td>
            <td>{{ exame.tipo }}</td>
            <td>{{ exame.resultado }}</td>
            <td>{{ exame.dataTeste }}</td>
            <td>{{ exame.dataLeitura }}</td>
            <td>{{ exame.preenchimentoEspelho }}</td>
            <td>{{ exame.observacoes }}</td>
            <td>{{ exame.valor }}</td>
            <td>
              <div v-if="exame.amostra">
                <router-link :to="{ name: 'AmostraView', params: { amostraId: exame.amostra.id } }"
                  >{{ exame.amostra.protocolo }}
                </router-link>
              </div>
            </td>
            <td>
              <div v-if="exame.subamostra">
                <router-link :to="{ name: 'SubamostraView', params: { subamostraId: exame.subamostra.id } }"
                  >{{ exame.subamostra.subamostra }}
                </router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ExameView', params: { exameId: exame.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-outline-success btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Ver</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ExameEdit', params: { exameId: exame.id, amostraId: amostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-warning btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editar</span>
                  </button>
                </router-link>
                <b-button
                  v-if="verificaUsuario()"
                  v-on:click="prepareRemove(exame)"
                  variant="danger"
                  class="btn btn-danger"
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
    <router-link :to="{ name: 'Amostra' }" custom v-slot="{ navigate }">
      <button type="submit" v-on:click.prevent="previousState()" class="btn btn-outline-success" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Voltar</span>
        </button></router-link>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="rp6App.exame.delete.question" data-cy="exameDeleteDialogHeading">Confirmação de exclusão</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-exame-heading">Você tem certeza que deseja deletar este Exame?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancelar</button>
        <button
          type="button"
          class="btn btn-danger"
          id="jhi-confirm-delete-exame"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeExame()"
        >
          Deletar
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./exame.component.ts"></script>
