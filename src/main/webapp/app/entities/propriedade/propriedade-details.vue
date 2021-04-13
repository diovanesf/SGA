<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="propriedade">
        <h2 class="jh-entity-heading" data-cy="propriedadeDetailsHeading"><span>Propriedade</span> {{ propriedade.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Tipo</span>
          </dt>
          <dd>
            <span>{{ propriedade.tipo }}</span>
          </dd>
          <dt>
            <span>Numero Animais</span>
          </dt>
          <dd>
            <span>{{ propriedade.numeroAnimais }}</span>
          </dd>
          <dt>
            <span>Acometidos</span>
          </dt>
          <dd>
            <span>{{ propriedade.acometidos }}</span>
          </dd>
          <dt>
            <span>Observacoes</span>
          </dt>
          <dd>
            <span>{{ propriedade.observacoes }}</span>
          </dd>
          <dt>
            <span>Pricipal Suspeita</span>
          </dt>
          <dd>
            <span>{{ propriedade.pricipalSuspeita }}</span>
          </dd>
          <dt>
            <span>Endereco</span>
          </dt>
          <dd>
            <div v-if="propriedade.endereco">
              <router-link :to="{ name: 'EnderecoView', params: { enderecoId: propriedade.endereco.id } }">{{
                propriedade.endereco.endereco
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="propriedade.id"
          :to="{ name: 'PropriedadeEdit', params: { propriedadeId: propriedade.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./propriedade-details.component.ts"></script>
