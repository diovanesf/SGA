<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="exame">
        <h2 class="jh-entity-heading" data-cy="exameDetailsHeading"><span>Exame</span> {{ exame.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Nome</span>
          </dt>
          <dd>
            <span>{{ exame.nome }}</span>
          </dd>
          <dt>
            <span>Tipo</span>
          </dt>
          <dd>
            <span>{{ exame.tipo }}</span>
          </dd>
          <dt>
            <span>Resultado</span>
          </dt>
          <dd>
            <span>{{ exame.resultado }}</span>
          </dd>
          <dt>
            <span>Data Teste</span>
          </dt>
          <dd>
            <span>{{ exame.dataTeste }}</span>
          </dd>
          <dt>
            <span>Data Leitura</span>
          </dt>
          <dd>
            <span>{{ exame.dataLeitura }}</span>
          </dd>
          <dt>
            <span>Preenchimento Espelho</span>
          </dt>
          <dd>
            <span>{{ exame.preenchimentoEspelho }}</span>
          </dd>
          <dt>
            <span>Observações</span>
          </dt>
          <dd>
            <span>{{ exame.observacoes }}</span>
          </dd>
          <dt>
            <span>Valor</span>
          </dt>
          <dd>
            <span>{{ exame.valor }}</span>
          </dd>
          <dt>
            <span>Amostra</span>
          </dt>
          <dd>
            <div v-if="exame.amostra">
              <router-link :to="{ name: 'AmostraView', params: { amostraId: exame.amostra.id } }">{{
                exame.amostra.protocolo
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Subamostra</span>
          </dt>
          <dd>
            <div v-if="exame.subamostra">
              <router-link :to="{ name: 'SubamostraView', params: { subamostraId: exame.subamostra.id } }">{{
                exame.subamostra.subamostra
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-outline-success" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Voltar</span>
        </button>
        <router-link v-if="exame.id" :to="{ name: 'ExameEdit', params: { exameId: exame.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-success">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Editar</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./exame-details.component.ts"></script>
