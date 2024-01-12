package top.mitrecx.dazhixianxian.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DzResponse<T> {
    // 00 表示成功, 其他为失败
    private String code;
    private String message;
    private T data;

    public DzResponse() {
    }

    public DzResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态内部类Builder
    public static class Builder<T> {
        private String code;
        private String message;
        private T data;

        // 构造方法，至少需要传入code
        public Builder(String code) {
            this.code = code;
        }

        // 可选的方法，用于设置message
        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        // 可选的方法，用于设置data
        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        // 构建DzResponse对象的方法
        public DzResponse<T> build() {
            // 在这里可以进行一些构建前的验证操作
            return new DzResponse<>(code, message, data);
        }
    }
}
