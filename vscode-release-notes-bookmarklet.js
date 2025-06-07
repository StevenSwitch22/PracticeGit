// VSCode Release Notes 保存书签脚本
// 使用方法：
// 1. 在浏览器中打开 VSCode release notes 页面
// 2. 运行此脚本（可以保存为书签）
// 3. 脚本会自动下载页面内容为 HTML 文件

javascript:(function(){
    // 获取页面标题和内容
    const title = document.title;
    const content = document.documentElement.outerHTML;
    
    // 创建文件名
    const now = new Date();
    const timestamp = now.toISOString().slice(0,19).replace(/:/g, '-');
    const filename = `VSCode_Release_Notes_${timestamp}.html`;
    
    // 创建下载链接
    const blob = new Blob([content], { type: 'text/html' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    a.style.display = 'none';
    
    // 触发下载
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    
    // 显示成功消息
    const notification = document.createElement('div');
    notification.innerHTML = `✅ Release Notes 已保存为: ${filename}`;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background: #4CAF50;
        color: white;
        padding: 15px;
        border-radius: 5px;
        z-index: 10000;
        font-family: Arial, sans-serif;
        box-shadow: 0 2px 10px rgba(0,0,0,0.3);
    `;
    document.body.appendChild(notification);
    
    // 3秒后移除通知
    setTimeout(() => {
        if (notification.parentNode) {
            notification.parentNode.removeChild(notification);
        }
    }, 3000);
})();
