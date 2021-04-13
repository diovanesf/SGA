<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="amostra">
        <h2 class="jh-entity-heading" data-cy="amostraDetailsHeading"><span>Amostra</span> {{ amostra.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Protocolo</span>
          </dt>
          <dd>
            <span>{{ amostra.protocolo }}</span>
          </dd>
          <dt>
            <span>Forma Envio</span>
          </dt>
          <dd>
            <span>{{ amostra.formaEnvio }}</span>
          </dd>
          <dt>
            <span>Numero Amostras</span>
          </dt>
          <dd>
            <span>{{ amostra.numeroAmostras }}</span>
          </dd>
          <dt>
            <span>Especie</span>
          </dt>
          <dd>
            <span>{{ amostra.especie }}</span>
          </dd>
          <dt>
            <span>Material Recebido</span>
          </dt>
          <dd>
            <span>{{ amostra.materialRecebido }}</span>
          </dd>
          <dt>
            <span>Acondicionamento</span>
          </dt>
          <dd>
            <span>{{ amostra.acondicionamento }}</span>
          </dd>
          <dt>
            <span>Condicao Material</span>
          </dt>
          <dd>
            <span>{{ amostra.condicaoMaterial }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ amostra.status }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="amostra.id" :to="{ name: 'AmostraEdit', params: { amostraId: amostra.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./amostra-details.component.ts"></script>
