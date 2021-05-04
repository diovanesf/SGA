<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="midia">
        <h2 class="jh-entity-heading" data-cy="midiaDetailsHeading"><span>Mídia</span> {{ midia.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Nome</span>
          </dt>
          <dd>
            <span>{{ midia.nome }}</span>
          </dd>
          <dt>
            <span>Descrição</span>
          </dt>
          <dd>
            <span>{{ midia.descricao }}</span>
          </dd>
          <dt>
            <span>Mídia</span>
          </dt>
          <dd>
            <div v-if="midia.file">
              Arquivo: {{ midia.fileContentType }} <br> Tamanho: {{ byteSize(midia.file) }} <br>
              <button class = "btn btn-outline-success"><a v-on:click="openFile(midia.fileContentType, midia.file)">Abrir</a></button>
            </div>
          </dd>
          <dt>
            <span>Amostra</span>
          </dt>
          <dd>
            <div v-if="midia.amostra">
              <router-link :to="{ name: 'AmostraView', params: { amostraId: midia.amostra.id } }">{{
                midia.amostra.protocolo
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-outline-success" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Voltar</span>
        </button>
        <router-link v-if="midia.id" :to="{ name: 'MidiaEdit', params: { midiaId: midia.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-success">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Editar</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./midia-details.component.ts"></script>
