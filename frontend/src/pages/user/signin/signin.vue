<template>
    <f7-page class="user-singin">
		
		<f7-navbar title="로그인 하기"></f7-navbar>
		
		<logo></logo>

		<f7-list no-hairlines>

			<f7-list-input
				label="아이디"
				type="text"
				placeholder="Your name"
				info="Default validation"
				required
				validate
				clear-button
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			</f7-list-input>

			<f7-list-input
				label="Fruit"
				type="text"
				placeholder="Type 'apple' or 'banana'"
				required
				validate
				pattern="apple|banana"
				clear-button
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			  <span slot="info">Pattern validation (<b>apple|banana</b>)</span>
			</f7-list-input>
		</f7-list>
    </f7-page>
</template>

<script>
import logo from '{COMPONENT}/logo/logo.vue'
export default { 
    components : {
    logo
  },
}
</script>