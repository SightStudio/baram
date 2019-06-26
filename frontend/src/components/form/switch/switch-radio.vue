<template>
    <div class="switch-field">
		<label v-for="(radio, idx) in radioList" :key="idx">
            <input 
                type="radio" 
                :name="radio.name" 
                :value="radio.val" 
                :checked="idx == 0 ? true : false"/>
            
            <p>{{ radio.desc }}</p>
        </label>
	</div>
</template>
<script>
export default {
    props: {
        radioList : {
            type : Array,
            default () {
                return []
            },
        }
    }
}
</script>
<style scoped>
.switch-field {	display: flex; overflow: hidden; }
.switch-field input { position: absolute; clip: rect(0, 0, 0, 0); height: 1px; width: 1px; border: 0; overflow: hidden; }
.switch-field input + p { padding: 6px 14px; border: 1px solid rgba(0, 0, 0, 0.2);}
.switch-field input:checked + p { background-color: #a5dc86;box-shadow: none;}

.switch-field label { background-color: #e4e4e4; color: rgba(0, 0, 0, 0.6); font-size: 12px; line-height: 1; text-align: center; margin-right: -1px;  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.3), 0 1px rgba(255, 255, 255, 0.1); transition: all 0.1s ease-in-out;}
.switch-field label:hover {	cursor: pointer; }
.switch-field label:first-of-type { border-radius: 4px 0 0 4px; }
.switch-field label:last-of-type { border-radius: 0 4px 4px 0; }
</style>




