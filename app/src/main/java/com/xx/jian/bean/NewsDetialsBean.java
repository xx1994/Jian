package com.xx.jian.bean;

import com.xx.jian.interf.RBResponse;

import java.util.List;

/**
 * Created by xx1994 on 2017/1/4.
 */
public class NewsDetialsBean extends RBResponse {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">

     <div class="img-place-holder"></div>



     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title"></h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic1.zhimg.com/da8e974dc_is.jpg">
     <span class="author">知乎用户，</span><span class="bio">认知心理学博士</span>
     </div>

     <div class="content">
     <p>题主说了&ldquo;确切&rdquo;二字，想必也是因为我们的主观反应是两者之间有关系，只是不知道科学界有啥确凿的证据而已。科学思维模式用到这个问题上，我自己理解是想寻找一条性格到疾病的产生通路。</p>
     <p>首先性格是个相对稳态的东西，而情绪是面对外界事件之后的反应，所以是流动变化的。关于性格的分类理论千千万，九型人格、big 5、MBTI 等等；关于性格形成的理论也是众说纷纭，不过逃不开先天基因加上后天成长环境，只是两者分成还没有定论。想想咱小时候爸妈说的，&ldquo;三岁看老&rdquo;，其实也不是没道理；之前 BBC 做过一集子宫日记，检测双胞胎在母体中的活动，其中一个活跃无比动来动去，另一个就喜欢吃手指抓着脐带；生下来之后追踪到幼儿园，发现在外界环境一样的情况下，俩双胞胎表现出的性格和他们在娘亲子宫里的是一毛一样。虽然这个只是单一案例长时间追踪，未必能推广到全部人群，但起码告诉我们，人的性格成型得比我们想象中还早。我们长大之后学会根据场合环境做出正常的社交反应，但不代表性格改变，只是行为上的改变。</p>
     <p>其次就是生理上的疾病这里所说的不包括外伤和心理疾病，因为例如&ldquo;外向型人格比较容易骨折&rdquo;和&ldquo;内向的人比较容易得抑郁症&rdquo;，大约是会得到一个非显著结果的，人话就是没啥关系。</p>
     <p>然后在我啰嗦的背景铺陈之下再来看看西方一些研究发现。我自己印象比较深的是 Timothy Smith 2006 年的一份总结性文章，发表在临床心理学年度回顾刊物上。他发现和性格比较有关系的生理问题就是寿命和心血管类疾病，这些性格特征是：消极情感 / 神经质，长期惯性愤怒 / 敌意和相关特质；而具备保护性的性格特质是：乐观。而生物 / 生理学的性格到疾病的这条通路是&mdash;&mdash;压力(Stress model)。Stress hormone,学名 Cortisol 皮质醇，已经变成了健康的头号公敌。</p>
     <p>茹毛饮血时代，人类感知到危险（多半是被吃掉的危险），要干嘛？跑啊。所以在保命时代，皮质醇不是那么坏，它会增加血液中的葡萄糖，给大脑和身体供给能量，血压也会升高。而在自然危险解除之后，我们的身体自然就会恢复常态，所以血糖血压就正常了。但是在神经质和一直很生气人群里，就算外界没有特别明显的&ldquo;威胁&rdquo;，他们的压力一直 up up up，于是皮质醇就一直很活跃，于是血糖血压值就慢慢爬升中。这会导致焦虑、抑郁、头疼、睡眠问题、心脏问题、还会胖&hellip;&hellip;生活如此艰难&hellip;&hellip;</p>
     <p>不过不是说天生比较神经质就一定会比较容易生病，如果外界环境相对有保障，也没有那么大压力的状况下，神经质的人群也可以放松生活。而且 smith 还有篇 paper 专门献给了我们性格中最大的保护神&mdash;&mdash;乐观。毕竟性格不是非 1 即 0，非黑即白，虽然也许早期就形成大部分，还是可以通过后天培养而往更阳光灿烂的方向去。</p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/41444162">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>


     </div>
     </div>
     * image_source : 《红楼梦》
     * title : 「性格」和「疾病」之间是否存在着确切的联系？
     * image : http://pic4.zhimg.com/c1a3f4fc9b1e0a5a0aca9752c305a09f.jpg
     * share_url : http://daily.zhihu.com/story/9122110
     * js : []
     * ga_prefix : 010416
     * images : ["http://pic2.zhimg.com/270251016486c3319ce843808b730ba1.jpg"]
     * type : 0
     * id : 9122110
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    private List<String> images;
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
