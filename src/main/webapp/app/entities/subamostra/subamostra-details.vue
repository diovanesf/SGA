<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="subamostra">
        <h2 class="jh-entity-heading" data-cy="subamostraDetailsHeading"><span>Subamostra</span> {{ subamostra.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Sub Amostra</span>
          </dt>
          <dd>
            <span>{{ subamostra.subAmostra }}</span>
          </dd>
          <dt>
            <span>Amostra</span>
          </dt>
          <dd>
            <div v-if="subamostra.amostra">
              <router-link :to="{ name: 'AmostraView', params: { amostraId: subamostra.amostra.id } }">{{
                subamostra.amostra.protocolo
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="subamostra.id"
          :to="{ name: 'SubamostraEdit', params: { subamostraId: subamostra.id } }"
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

<script lang="ts" src="./subamostra-details.component.ts"></script>
