package top.mitrecx.dazhixianxian.common;

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

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static <TT> DzResponse<TT> ok(TT data) {
        return new DzResponse<>(BizCode.OK.code, BizCode.OK.getMessage(), data);
    }

    public static DzResponse<Void> ok() {
        return new DzResponse<>(BizCode.OK.code, BizCode.OK.getMessage(), null);
    }

    public static <TT> DzResponse<TT> fail(BizCode bizCode, TT data) {
        return new DzResponse<>(bizCode.code, bizCode.getMessage(), data);
    }

    public static DzResponse<?> fail(BizCode bizCode) {
        return new DzResponse<>(bizCode.code, bizCode.getMessage(), null);
    }

    public static DzResponse<?> fail(BizCode bizCode, String message) {
        String msg = String.format(bizCode.getMessage(), message);
        return new DzResponse<>(bizCode.code, msg, null);
    }

    // 静态内部类Builder
    public static class Builder<T> {
        private String code;
        private String message;
        private T data;

        public Builder() {
        }

        public Builder<T> code(String code) {
            this.code = code;
            return this;
        }

        // 可选的方法，用于设置message
        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> ok(T data) {
            this.code = BizCode.OK.code;
            this.message = BizCode.OK.getMessage();
            this.data = data;
            return this;
        }

        public Builder<T> ok() {
            this.code = BizCode.OK.code;
            this.message = BizCode.OK.getMessage();
            return this;
        }

        public Builder<T> fail(BizCode bizCode, T data) {
            fail(bizCode);
            this.data = data;
            return this;
        }

        public Builder<T> fail(BizCode bizCode) {
            this.code = bizCode.code;
            this.message = bizCode.getMessage();
            return this;
        }

        public Builder<T> fail(BizCode bizCode, String message) {
            this.code = bizCode.code;
            this.message = String.format(bizCode.getMessage(), message);
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
