#!/usr/bin/env python3
"""
VSCode Release Notes Fetcher
自动获取 VSCode 最新版本的 release notes 并保存到本地文件
"""

import requests
import json
import os
from datetime import datetime
from bs4 import BeautifulSoup
import argparse

def fetch_latest_release_notes():
    """获取最新的 VSCode release notes"""
    try:
        # 获取 VSCode 更新页面
        response = requests.get("https://code.visualstudio.com/updates")
        response.raise_for_status()
        
        soup = BeautifulSoup(response.content, 'html.parser')
        
        # 查找最新版本链接
        updates_list = soup.find('ul')
        if updates_list:
            first_link = updates_list.find('a')
            if first_link:
                latest_version_url = "https://code.visualstudio.com" + first_link['href']
                
                # 获取具体版本的 release notes
                version_response = requests.get(latest_version_url)
                version_response.raise_for_status()
                
                return {
                    'url': latest_version_url,
                    'content': version_response.text,
                    'version': first_link.text.strip()
                }
    except Exception as e:
        print(f"获取 release notes 失败: {e}")
        return None

def save_release_notes(release_data, output_dir="vscode_releases"):
    """保存 release notes 到本地文件"""
    if not release_data:
        return None
    
    # 创建输出目录
    os.makedirs(output_dir, exist_ok=True)
    
    # 生成文件名
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    version = release_data['version'].replace(' ', '_').replace('(', '').replace(')', '')
    filename = f"{timestamp}_{version}.html"
    filepath = os.path.join(output_dir, filename)
    
    # 保存内容
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(release_data['content'])
    
    # 保存元数据
    metadata = {
        'timestamp': timestamp,
        'version': release_data['version'],
        'url': release_data['url'],
        'filename': filename
    }
    
    metadata_file = os.path.join(output_dir, f"{timestamp}_metadata.json")
    with open(metadata_file, 'w', encoding='utf-8') as f:
        json.dump(metadata, f, indent=2, ensure_ascii=False)
    
    return filepath

def main():
    parser = argparse.ArgumentParser(description='获取 VSCode Release Notes')
    parser.add_argument('--output', '-o', default='vscode_releases', 
                       help='输出目录 (默认: vscode_releases)')
    parser.add_argument('--format', '-f', choices=['html', 'text'], default='html',
                       help='输出格式 (默认: html)')
    
    args = parser.parse_args()
    
    print("正在获取最新的 VSCode release notes...")
    release_data = fetch_latest_release_notes()
    
    if release_data:
        filepath = save_release_notes(release_data, args.output)
        if filepath:
            print(f"✅ Release notes 已保存到: {filepath}")
            print(f"📝 版本: {release_data['version']}")
            print(f"🔗 原始链接: {release_data['url']}")
        else:
            print("❌ 保存文件失败")
    else:
        print("❌ 获取 release notes 失败")

if __name__ == "__main__":
    main()
