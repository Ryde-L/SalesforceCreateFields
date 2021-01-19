package ryde;

/**
 * 接口请求体
 */
public class RequestBodyEntity {
    //元数据
    public Metadata metadata;
    //对象API名.字段API名
    public String fullName;

    public RequestBodyEntity(){
        metadata=new Metadata();
    }

}

class Metadata{

    //字段类型
    public String type;
    //字段描述
    public String description;
    //公式
    public String formula;
    //选项列表设置
    public ValueSet valueSet;
    //数字/币种总长度
    public Integer precision;
    //数字/币种精度
    public Integer scale;
    //标签
    public String label;
    //文本长度
    public Integer length;
    //必填
    public boolean required;

    public Metadata(){
        required=false;
    }



}

//选项列表设置
class ValueSet{
    //受限
    public Boolean restricted;
    //
    public ValueSetDefinition valueSetDefinition;

    public ValueSet(){
        restricted=true;
        valueSetDefinition=new ValueSetDefinition();
    }

    //选项列表定义
    static class ValueSetDefinition{
        public Boolean sorted;
        public Value[] value;

        public ValueSetDefinition(){
            sorted=true;
        }

        static class Value{
            //是否默认
//            public boolean Default;
            //描述
            public String description;
            //启用
            public Boolean isActive;
            //标签
            public String label;
            //选项值
            public String valueName;
            //
            public String urls;

            public Value(){
                isActive=null;
            }

        }
    }
}
