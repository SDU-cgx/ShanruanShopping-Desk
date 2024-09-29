<template>
  <div class="comment">
    <h3 v-if="comments.length==0">当前没有任何评论！</h3>
    <CommodityCommentListItem 
      v-for="comment in comments" 
      :item="comment" 
      :key="comment.id"/>
  </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
  import CommodityCommentListItem from './CommodityCommentListItem'
  
  export default {
    name: 'CommodityCommentList',
    data () {
      return {
        dialogVisible:false,
        evaluation1:"",
        score: null,
        colors: ['#99A9BF', '#F7BA2A', '#FF9900']   //星级评定不同分数的颜色
      };
    },
    computed:{
      ...mapState('user',['user']),
      ...mapState({comments:'evaluation'}),
      message(){
        return this.comments.length === 0 ? '当前没有任何评论！' : '';
      }
    },
    components: {
      CommodityCommentListItem,
    },

    created(){
      let url = "/commodityEvaluation"+this.$route.path;
      if(url.indexOf("/shop/commodity")!=-1){
          url = "/commodityEvaluation/commodity/"+this.$route.params.id;
      }
      this.axios.get(url,{params:{page:1}})
          .then(response => {
            if(response.status == 200){              
              this.getEvaluation(response.data.data.records);
            }
          })
          .catch(error => alert(error));
    },

    methods:{
      //写评论
      writeComment(){
        if(this.user)
          this.dialogVisible = true
        else
          this.$router.push("/login")
      },

      addSubmit(){
        var data = {};
        data.commodityId = this.$route.params.id;
        data.score = this.score;
        data.evaluation = this.evaluation1;
        data.userId = this.user.id;
        this.axios.post("/commodityEvaluation",data).then(response => {
            if(response.data.code == 0){
              this.addEvaluation(data);
              this.dialogVisible  = false;
              this.$message({
                type: 'success',
                message: "评论成功！"
              })  
            }
        })
      },

      ...mapMutations(['addEvaluation','getEvaluation']),

       handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            done();
          })
          .catch(() => {});
      }
    }
  }

</script>
<style scoped>
.comment{
  position: relative;
}
.comment .commentbt{
  position: absolute;
  top: 10px;
  right: 50px;
}
</style>