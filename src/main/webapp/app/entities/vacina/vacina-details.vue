<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="vacina">
        <h2 class="jh-entity-heading" data-cy="vacinaDetailsHeading"><span>Vacina</span> {{ vacina.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ vacina.status }}</span>
          </dd>
          <dt>
            <span>Peso Material</span>
          </dt>
          <dd>
            <span>{{ vacina.pesoMaterial }}</span>
          </dd>
          <dt>
            <span>Estimativa Vacinas</span>
          </dt>
          <dd>
            <span>{{ vacina.estimativaVacinas }}</span>
          </dd>
          <dt>
            <span>Data Conclusao</span>
          </dt>
          <dd>
            <span>{{ vacina.dataConclusao }}</span>
          </dd>
          <dt>
            <span>Observacoes</span>
          </dt>
          <dd>
            <span>{{ vacina.observacoes }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="vacina.id" :to="{ name: 'VacinaEdit', params: { vacinaId: vacina.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./vacina-details.component.ts"></script>
